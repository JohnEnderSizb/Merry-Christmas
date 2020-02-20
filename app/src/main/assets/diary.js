function last() {
    $("html, body").animate({ scrollTop: $(document).height() }, "slow");
}


$(function() {
        var entrys = JSReceiver.loadEntrys();
        //$('#test').text(entrys);
        //alert("food");
        entrysJSON = JSON.parse(entrys);

        //$('#test').text("say goodnight");

        outcome = entrysJSON.outcome;

        //$('#test').text(outcome);



        if(outcome == "found") {
            theEntrys = entrysJSON.entries;

            for (i = 0; i < theEntrys.length; i++) {
                var entryInner = theEntrys[i].the_entry
                var entryTittle = theEntrys[i].entry_title
                var entryTime = theEntrys[i].entry_time
                var entry_id = theEntrys[i].the_id

                var $anHTMLEntry = $("#entry_template").clone();
                $("#the_entries_div").append($anHTMLEntry);

                the_time_phew = entryTime[3] + entryTime[4] + entryTime[5] +entryTime[6] + entryTime[7] + entryTime[8] + entryTime[9] + entryTime[10] + entryTime[11] + entryTime[12] + entryTime[13] + entryTime[14] + entryTime[15] ;

                $("h4.entry-title", $anHTMLEntry).text(entryTittle);
                $("span.entry-message-field", $anHTMLEntry).text(entryInner);
                $("span.entry-time-field", $anHTMLEntry).text(the_time_phew);

                //$anHTMLEntry.aField = "foood_find_me";

                $anHTMLEntry.show();

            }

        }



    }
);


function openEntry(entry) {
    //$("#test").text(entry.aField);
    //entryId = entry.id;
}

function showNew(theEntryEntered) {


    entrysJSON = JSON.parse(theEntryEntered);

    var entryInner = entrysJSON.theEnteredEntry
    var entryTittle = entrysJSON.the_entered_title
    var entryTime = entrysJSON.the_entry_time

    //$("#test").text(entryInner);

    var $anHTMLEntry = $("#entry_template").clone();
    $("#the_entries_div").prepend($anHTMLEntry);

    the_time_phew = entryTime[3] + entryTime[4] + entryTime[5] +entryTime[6] + entryTime[7] + entryTime[8] + entryTime[9] + entryTime[10] + entryTime[11] + entryTime[12] + entryTime[13] + entryTime[14] + entryTime[15] ;

    $("h4.entry-title", $anHTMLEntry).text(entryTittle);
    $("span.entry-message-field", $anHTMLEntry).text(entryInner);
    $("span.entry-time-field", $anHTMLEntry).text(the_time_phew);

    //$anHTMLEntry.aField = "foood_find_me";

    $anHTMLEntry.show("slow");

}




/*
dateObj = new Date(1577180338249 * 1000);
hours = dateObj.getHours();
minutes = dateObj.getMinutes();
timeString = hours.toString().padStart(2, '0') + ":" + minutes;
$('#test').text(timeString);


*/



/*
var $anHTMLEntry = $("#entry_template").clone();
$("#the_entries_div").append($anHTMLEntry);
$("h4.entry-title", $anHTMLEntry).text("Ohhhhh Reaper");
$anHTMLEntry.show("slow");*/