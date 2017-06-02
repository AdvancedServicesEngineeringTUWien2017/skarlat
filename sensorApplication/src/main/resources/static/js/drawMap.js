
    var numberOfSensors = document.getElementById("sensorsTable").rows.length;  //number of all sensors
    var table = document.getElementById("sensorsTable");  //table to map the data from sensors
    var svg = document.getElementById("map_picture"); //we have one svg element to draw the ontology graph on it

         var frameRect = document.createElementNS("http://www.w3.org/2000/svg", 'rect');//this is the frame rectangle of the graph
         frameRect.setAttribute("x", 0);
         frameRect.setAttribute("y", 0);
         frameRect.setAttribute("width", 572);
         frameRect.setAttribute("height", 259);
         frameRect.setAttribute("fill", "url(#imgpattern)");
         frameRect.setAttribute("stroke", "#ccc");
         frameRect.setAttribute("stroke-width", 1);
         svg.appendChild(frameRect);

    for (var i=0; i<numberOfSensors;i++){
         var frameRect = document.createElementNS("http://www.w3.org/2000/svg", 'rect');//this is the frame rectangle of the graph
         frameRect.setAttribute("x", table.rows[i].cells[3].textContent);
         frameRect.setAttribute("y", table.rows[i].cells[4].textContent);
         frameRect.setAttribute("width", 3);
         frameRect.setAttribute("height", 3);
         frameRect.setAttribute("fill", "orange");
         frameRect.setAttribute("stroke", "orange");
         frameRect.setAttribute("stroke-width", 1);
         svg.appendChild(frameRect);
    }