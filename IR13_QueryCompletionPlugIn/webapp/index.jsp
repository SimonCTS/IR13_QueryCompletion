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
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
    <link href="css/bootstrap-responsive.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="css/application.css">
    <link rel="icon" href="favicon.ico" type="image/ico"></link>
    <link rel="shortcut icon" href="favicon.ico" type="image/ico"></link>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/bootstrap.js"></script>
    <script type="text/javascript" src="js/query_complete.js"></script>

    <title>Welcome to Solr</title>
  </head>
  <body>

    <div class="container-narrow">
      <div class="masthead">
        <ul class="nav nav-pills pull-right">
          <li>
            <a href="admin/" class="text-right">Solr Admin</a></br>
          </li>
          <li>
            <a href="#" id="update-suggestions">Build suggestions</a>
          </li>
        </ul>
        <h3 class="muted">Query completion</h3>
      </div>
      
      <div class="jumbotron">
        <div class="search-query"></div>
        <form class="form-search" name="queryForm" accept-charset="UTF-8">
          <fieldset>
            <legend>
              <h1>
                Enter your query:
              </h1>
            </legend>
            
            <input type="text" id="search-field" class="typeahead input-mysize search-field" placeholder="Type something.." name="query" autocomplete="off" data-items="4" data-minLength="1" data-provide="typeahead" data-source='[]'></input>
            
            </input>
          </fieldset>
        </form>
      </div>
      <div class="row-fluid marketing">
        <div class="span12 result-json"></div>
      </div>
      <hr>
      <div class="row-fluid marketing">
        <div class="span12 result-list">
          <h5>Results:</h5>
          <ul id="#result-list" class="unstyled"> 
            
          </table>
        </div>
      </div>
    </div>
  </body>
</html>
