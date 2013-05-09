package ir13.achandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.ArrayUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.request.LukeRequest;
import org.apache.solr.client.solrj.response.LukeResponse;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.CommonParams;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.handler.RequestHandlerBase;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.response.SolrQueryResponse;

public class AutoCompleteHandler extends RequestHandlerBase {
	private static final String SERVER_ADDRESS = "http://localhost:8983/solr";
	private SolrServer server;


	/**
	 * Parsing of the incoming query. Must identify the kind of stuff we are
	 * looking for
	 * 
	 * TODO: Decide on rules for deciding action. Multiple attributes possible!
	 * 
	 * @param q
	 * @return an AcRequest structure
	 */
	private AcRequest parseQuery(String q) {
		
	Pattern pattern1 = Pattern.compile("(\\w++):\\((\\w*+) ?$");
	Pattern pattern2 = Pattern.compile(" $");
		if(q == null || q.isEmpty())
			return new AcRequest();		// Request is empty!
		
		//This is a field request?
		System.out.println(q);
		Matcher matcher = pattern1.matcher(q);
		if(matcher.find()) {
			System.out.println("hejhopp i lingonskogen");
			System.out.println(matcher.groupCount());
			System.out.println(matcher.group(1));System.out.println(matcher.group(2));
			return new AcRequest(AcRequest.FIELD, matcher.group(1), matcher.group(2), q.substring(0, q.length() - (matcher.group(1).length() + matcher.group(2).length())));
		}
		
		String[] words = q.split(" ");
		if(ArrayUtils.isEmpty(words))
			return new AcRequest();		// question is empty!
		ArrayUtils.reverse(words);
		
		//This is a syntax request?
		matcher = pattern2.matcher(q);
		if(matcher.find()) {
			return new AcRequest(AcRequest.SYNTAX, words[0], q.substring(0,words[0].length()));
		}
		
		//OK then it's regular
		System.out.println("word_length: " + words[0].length());
		System.out.println("gives me: " +  q.substring(0,(q.length() - words[0].length())));
		return new AcRequest(AcRequest.REGULAR, words[0], q.substring(0,(q.length() - words[0].length())));
	}

	/**
	 * Initialize the solr server we are going to use for other queries
	 */
	private void initSolrServer() {
		server = new HttpSolrServer(SERVER_ADDRESS);
	}

	/**
	 * Init the "suggest" requestHandler
	 */
	private void initSuggest() throws SolrServerException {
		SolrQuery query = new SolrQuery();
		query.setQueryType("/suggest");
		query.set("spellcheck.build", true);
		server.query(query);
	}

	/**
	 * Execute a search with suggest. TODO: Implement a selection of field for
	 * the query
	 * 
	 * @param an
	 *            auto-completion request
	 * @return the response
	 * @throws SolrServerException
	 * @throws IOException
	 */
	private AcResult executeSearchPrefix(AcRequest request)
			throws SolrServerException, IOException {
		SolrQuery queryAc = new SolrQuery();
		queryAc.setQueryType("/suggest");
		queryAc.set("q", request.getContent());

		AcResult result;
		if (request.isRegularRequest()) {
			result = doFieldSearch(request.getField(), request.getRoot());
		} else if(request.isFieldRequest()) {
			result = doContentSearch(request.getField(), request.getContent(), request.getRoot());
		} else if(request.isSyntaxRequest()) {
			result = doSyntaxQuery(request.getRoot(), request.getField());
		}else{/*empty*/
			result = handleEmpty();
		}
		return result;
	}

	private AcResult handleEmpty() {
		// TODO Auto-generated method stub
		return new AcResult(false, false, false);
	}

	private AcResult doSyntaxQuery(String root, String word) {
		return new AcResult(true, word, root);
	}

	private AcResult doFieldSearch(String field, String root) throws SolrServerException,
			IOException {
		LukeRequest queryLuke = new LukeRequest();
		LukeResponse lukeResponse = new LukeResponse();
		AcResult toReturn = null;
		Map<String, LukeResponse.FieldInfo> fields = null;
		

		ArrayList<String> matchingField = new ArrayList<String>();
		

		
		queryLuke.setShowSchema(true);
		queryLuke.setNumTerms(0);

		// Execution and extraction of response
		lukeResponse = queryLuke.process(server);
		fields = lukeResponse.getFieldInfo();

		// Selection of fields starting with the prefix <field>
		Iterator<Map.Entry<String, LukeResponse.FieldInfo>> iter = fields
				.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, LukeResponse.FieldInfo> entry = iter.next();
			String name = entry.getValue().getName();
			if (name.startsWith(field)) {
				matchingField.add(name);
			}
		}

		if (!matchingField.isEmpty()) {
			toReturn = new AcResult(true, matchingField, root);
		} else {
			toReturn = new AcResult(false, false, false);
		}

		return toReturn;
	}

	private AcResult doContentSearch(String field, String content, String root) throws SolrServerException {
		SolrQuery query = new SolrQuery();
		QueryResponse response = null;
		SolrDocumentList docs = null;
		ArrayList<String> contentResults = new ArrayList<String>();
		AcResult toReturn = null;
		
		/* build the query*/
		query.setQueryType("/select");
		query.set("q", field+":"+content+"*");
		query.set("fl", field);

		/*execute the query*/
		response = server.query(query);
		
		/*extraction of results*/
		docs = response.getResults();
		
		for(SolrDocument doc : docs){
			contentResults.add(doc.getFieldValue(field).toString());
		}
		
		if(contentResults.isEmpty()){
			toReturn = new AcResult(false, false, false);
		}else{
			toReturn = new AcResult(true, field, contentResults, root);
		}
		
		return toReturn;
	}

	/**
	 * Prepare the response returned by our handler. Takes the previous query's
	 * response into account.
	 * 
	 * @param res
	 * @param response
	 */
	private void formatResponse(SolrQueryResponse res, AcResult response) {

		
		if(response.isContent()){
			res.add("result", response.getContentList());
		}else if(response.isField()){
			res.add("result", response.getFieldsList());
		}else{
			System.err.println("error of boolean");
		}

	}

	@Override
	public void handleRequestBody(SolrQueryRequest req, SolrQueryResponse res)
			throws Exception {
		// extract params from request
		SolrParams params = req.getParams();
		String q = params.get(CommonParams.Q);

		// hopefully, q is now the string issued from the interface

		/*
		 * treatment of the query
		 */

		AcRequest result = parseQuery(q);
		//Hobbe debug:
		System.out.println("field: " + result.getField());
		System.out.println("content " + result.getContent());
		System.out.println((int)result.requestType());
		System.out.println();
		
		// use solrj to do some queries over solr

		/*
		 * initialization of solr server
		 */

		initSolrServer();

		/*
		 * Initialization of the suggest requestHandler
		 */

		initSuggest();

		/*
		 * Execution of the query
		 */
		AcResult response = executeSearchPrefix(result);

		/*
		 * Preparing the response
		 */
		formatResponse(res, response);

	}

	/* ====== Methods we don't use (for now ?) ====== */

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSource() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSourceId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getVersion() {
		// TODO Auto-generated method stub
		return null;
	}

}
