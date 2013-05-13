$( document ).ready(function() {
  console.log("Query Completion loaded..");
  console.log("Please type something in the textfield to test the autocomplete");

  $('#update-suggestions').click( function() {
    $.ajax({
      type: "GET",
      url: "suggest?spellcheck.build=true",
      beforeSend: function() {
        $('#update-suggestions').text("Building suggestions index. Please wait..");
      },
      success: function(data) {
        $('#update-suggestions').text("Done building suggestions!");
      }
    });
  });


  $(".search-field").keyup(function() {
    queryString = $('.search-field').val();

    queryString = queryString.replace(/ ([^ ]*)$/,'%20$1'); //a_bc

    $.ajax({
      type: "GET",
      dataType: "xml",
      url: "autocomplete?q=" + queryString,
      beforeSend: function() {
        $('.result-suggest ul').empty();
      },
      success: function(data) {
        $(".search-field").data("source").length = 0;
        typeaheadData = [];
        $(data).find('str').each(function() {
          text = $(this).text();
          text = text.replace(/  /g,' '); 
          //$(".search-field").data("source").push(text);
          typeaheadData.push(text);
          //console.log(text);
          $("#result-suggest ul").append(
            $('<li>').append(
              text));
        }).promise().done(function() {
          console.log("each done");
          console.log(typeaheadData);
          $( ".search-field" ).autocomplete({
            source: typeaheadData
          });
        });

        console.log($(".search-field").data("source"));
      },
      errors: function(data) {
        console.log("Some error");
      }
    });
  });

  $('#form-search').bind("keypress", function(e) {
    if (e.keyCode == 13) {               
      e.preventDefault();
      queryString = $("#search-field").val();
      $("#result-list ul").empty();

      $.ajax({
        type: "GET",
        dataType: "xml",
        url: "select?q=" + queryString,
        success: function(data) {
          console.log(data);
          $(data).find('str[name="name"]').each(function() {
            $("#result-list ul").append(
              $('<li>').append(
                $(this).text()));
          })
        }
      });
      return false;
    }
  });

  $(".search-field-old").keyup(function() {
    console.log("keypressed in search field");
    query = $('.search-field').val();
    console.log("searching for " + query);
    $.ajax({
      type: "GET",
      dataType: "xml",
      url: "select/?q=" + query + "&version=2.2&start=0&rows=10&indent=on",
      success: function(data) {
        $('.search-query').html(query);
        $(".result-list").html("");
        console.log("Ajax Request Completed. Data: ", data);
        console.log("data.text: ", $(data));
        $(data).find('doc').each(function() {
          $(".result-list").append("<li>" + $(this).text() + "</li>");
        });
        
      }
    });
  });
});
