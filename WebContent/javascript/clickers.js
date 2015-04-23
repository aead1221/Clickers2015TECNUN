function drawBarChart(array) {
	var data = google.visualization.arrayToDataTable([
		["Question", "Average Value", { role: "style" } ],
		["Q1", 4.5, "#990000"],
		["Q2", 2, "#990000"],
		["Q3", 4.7, "#990000"],
		["Q4", 3.5, "#990000"],
		["Q5", 3, "#990000"]
		]);
	
	var view = new google.visualization.DataView(data);
	view.setColumns([0, 1,
		{ calc: "stringify",
		sourceColumn: 1,
		type: "string",
		role: "annotation" },
		2]);
	
	var options = {
		width: 500,
		height: 300,
		bar: {groupWidth: "95%"},
		legend: { position: "none" },
		};
	var chart = new google.visualization.BarChart(document.getElementById("clk-stats-survey"));
	chart.draw(view, options);
}

function getTestStats() {
	
}

function drawPieChart() {
	
	var array = document.getElementsByName("clk-pie-data");
	
	var array = [1, 2, 3, 2, 10];
	
	var data = google.visualization.arrayToDataTable([
		['Task', 'Hours per Day'],
		['m=10',     array[0]],
		['9<=m<10',      array[1]],
		['7<=m<9',  array[2]],
		['5<=m<7', array[3]],
		['m<5',    array[4]]
		]);
	
	var options = {
		is3D: true,
		'width': 500,
		'height': 300,
		slices: [{color: '#6A0000'}, {color: '#990000'}, {color: '#B43838'}, {color: '#CE6161'}, {color: '#D7CFCF'}]
	};
	
	var chart = new google.visualization.PieChart(document.getElementById('clk-stats-test'));
	chart.draw(data, options);
}

function openReport() {
	var path = 'testReport.pdf';
	var win = window.open(path);
	if(win){
	    win.focus();
	}else{
	    alert('Please allow popups for this site', 'window');
	}
}

function openReportS() {
	var path = 'surveyReport.pdf';
	var win = window.open(path);
	if(win){
	    win.focus();
	}else{
	    alert('Please allow popups for this site', 'window');
	}
}

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
        document.getElementById("ts-ns-input").innerHTML = '<td id= colspan="7"><input class="clk-num" type="number" min="2" step="1" value="2" name="op"><br><br>';
        document.getElementById("ts-cb").style.display = 'inline';
        document.getElementById("ts-p-text").style.color = 'black';
        document.getElementById("ts-continue").innerHTML = '<a href="TestContent.html"><button class="clk-button">Continue</button></a>';
    } else {
        if (ts[1].checked) {
            document.getElementById("ts-ns").innerHTML = 'Survey scale:';
            document.getElementById("ts-ns-input").innerHTML = '<td id= colspan="7"><select class="clk-cb"><option>2 - Yes/No</option><option>3 - Yes/Neutral/No</option><option>5 - Strongly agree/Agree/Neutral/Disagree/Strongly Disagree</option></select><br><br>';
            document.getElementById("ts-cb").style.display = 'none';
            document.getElementById("ts-p-text").style.color = 'white';
            document.getElementById("ts-p").style.display = 'none';
            document.getElementById("ts-continue").innerHTML = '<a href="SurveyContent.html"><button class="clk-button">Continue</button></a>';
        } else {
            document.getElementById("ts-ns").innerHTML = '';
            document.getElementById("ts-ns-input").innerHTML = '';
            document.getElementById("ts-cb").style.display = 'none';
            document.getElementById("ts-p-text").style.color = 'white';
            document.getElementById("ts-continue").innerHTML = '';
        }   
    }
}