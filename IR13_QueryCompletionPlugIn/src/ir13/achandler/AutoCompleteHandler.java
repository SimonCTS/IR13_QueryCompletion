package ir13.achandler;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
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
	 * @param q
	 * @return an AcRequest structure
	 */
	private AcRequest parseQuery(String q) {
		return new AcRequest("field", q);
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
	 */
	private QueryResponse executeSearchPrefix(AcRequest request)
			throws SolrServerException {
		SolrQuery queryAc = new SolrQuery();
		queryAc.setQueryType("/suggest");
		queryAc.set("q", request.getContent());
		return server.query(queryAc);
	}

	/**
	 * Prepare the response returned by our handler. Takes the previous query's response
	 * into account.
	 * @param res
	 * @param response
	 */
	private void formatResponse(SolrQueryResponse res, QueryResponse response) {
		res.add("toto", "hello");
		res.add("data2", 42);
		res.add("data3", true);
		res.add("response", response);
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
		QueryResponse response = executeSearchPrefix(result);

		/*
		 * Preparing the response
		 */
		formatResponse(res, response);


	}

	/*====== Methods we don't use (for now ?) ======*/
	
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
