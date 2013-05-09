<%@ page import="org.apache.solr.core.SolrCore" %>
<%@ page import="java.util.Collection" %>
<%--
 Licensed to the Apache Software Foundation (ASF) under one or more
 contributor license agreements.  See the NOTICE file distributed with
 this work for additional information regarding copyright ownership.
 The ASF licenses this file to You under the Apache License, Version 2.0
 (the "License"); you may not use this file except in compliance with
 the License.  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
--%>

<html>
  <head>
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="css/application.css">
    <link rel="icon" href="favicon.ico" type="image/ico"></link>
    <link rel="shortcut icon" href="favicon.ico" type="image/ico"></link>
    <title>Welcome to Solr</title>
  </head>
  <body>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/bootstrap.js"></script>
    <script type="text/javascript" src="js/query_complete.js"></script>
    
    <h1 class="text-center">Welcome to Solr!</h1>
    
    <a href="admin/" class="text-right">Solr Admin</a></br>
    <a href="#" id="update-suggestions">Build suggestions</a>
    
    <div class="row">
      <div class="span6 offset3">
        <div class="search-query"></div>
        <form class="form-search" name="queryForm" method="GET" action="select/" accept-charset="UTF-8">
          <fieldset>
            <legend>You could search for something</legend>
            
            <input type="text" id="search-field" class="typeahead input-large search-field" placeholder="Type something.." name="q" autocomplete="off" data-items="4" data-minLength="1" data-provide="typeahead" data-source='[]'></input>
            
            </input>
          </fieldset>
        </form>
      </div>
    </div>
    <div class="row">
      <div class="span6 offset3 result-json"></div>
      
    </div>
    <div class="row">
      <div class="span6 offset3 result">
        <ul class="result-list">
        </ul>
      </div>
    </div>
  </body>
</html>
