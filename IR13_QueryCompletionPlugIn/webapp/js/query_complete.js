$( document ).ready(function() {
  console.log("Query Completion loaded..");
  console.log("Please type something in the textfield to test the autocomplete");

  $('#update-suggestions').click( function() {
    $.ajax({
      type: "GET",
      url: "suggest?spellcheck.build=true",
      beforeSend: function() {
        console.log("building suggestions");
        $('#update-suggestions').text("Building suggestions index. Please wait..");
      },
      success: function(data) {
        console.log("build successfull");
        $('#update-suggestions').text("Done building suggestions!");
      }
    });
  });

  $(".search-field").keyup(function() {
    queryString = $('.search-field').val();
    prefix = $('.search-field').val().split(' ').pop();
    //console.log("strlen: " + text.length);
    
    //prefix = $('.search-field').val();
  
    console.log("Suggestion completetions for: " + prefix);
    $.ajax({
      type: "GET",
      dataType: "xml",
      url: "autocomplete?q=" + queryString,
      beforeSend: function() {
        $('.result-suggest').empty();
      },
      success: function(data) {
        console.log("suggestion worked");
        console.log( data);
        
        $(".search-field").data("source").length = 0;
        $(data).find('str').each(function() {
          $(".search-field").data("source").push($(this).text());

          $(".result-suggest").append("<li>" + $(this).text()+"</li>");
        });
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
      console.log("Searching for " + queryString);

      $.ajax({
        type: "GET",
        dataType: "xml",
        url: "select?q=" + queryString,
        success: function(data) {
          console.log(data);
          $(data).find('str[name="name"]').each(function() {
            console.log("one data: " + $(this).text());
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
