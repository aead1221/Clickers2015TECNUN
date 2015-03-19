function toggleP() {
    if (document.getElementById("ts-cb").checked) {
        document.getElementById("ts-p").style.display = 'inline';
    } else {
    document.getElementById("ts-p").style.display = 'none';
    }
}

function toggleTS() {
    var ts = document.getElementsByName("ts-ts");
    if (ts[0].checked) {
        document.getElementById("ts-ns").innerHTML = 'Number of options for each question:';
        document.getElementById("ts-ns-input").innerHTML = '<td id= colspan="7"><input type="text" name="op"><br><br>';
        document.getElementById("ts-cb").style.display = 'inline';
        document.getElementById("ts-p-text").style.color = 'black';
        document.getElementById("ts-continue").innerHTML = '<a href="TestContent.html"><button>Continue</button></a>';
    } else {
        if (ts[1].checked) {
            document.getElementById("ts-ns").innerHTML = 'Survey scale:';
            document.getElementById("ts-ns-input").innerHTML = '<td id= colspan="7"><select><option>2 - Yes/No</option><option>3 - Yes/Neutral/No</option><option>5 - Strongly agree/Agree/Neutral/Disagree/Strongly Disagree</option></select><br><br>';
            document.getElementById("ts-cb").style.display = 'none';
            document.getElementById("ts-p-text").style.color = 'white';
            document.getElementById("ts-p").style.display = 'none';
            document.getElementById("ts-continue").innerHTML = '<a href="SurveyContent.html"><button>Continue</button></a>';
        } else {
            document.getElementById("ts-ns").innerHTML = '';
            document.getElementById("ts-ns-input").innerHTML = '';
            document.getElementById("ts-cb").style.display = 'none';
            document.getElementById("ts-p-text").style.color = 'white';
            document.getElementById("ts-continue").innerHTML = '';
        }   
    }
}