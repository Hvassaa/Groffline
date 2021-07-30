// ace editor setup
let aceEditor = ace.edit("editor");
aceEditor.setTheme("ace/theme/monokai");

let cookie = document.cookie;
if (cookie !== "") {
  let savedInput = decodeURIComponent(cookie);
  aceEditor.setValue(savedInput);
  aceEditor.clearSelection();
} else {
  aceEditor.setValue(".TL\nTitle\n.LP\nA Paragraph.");
  aceEditor.clearSelection();
}

const windowLocation = window.location.href;

// get macro set
let macrosSelector = document.getElementById("macros-selector");

// fetching pdf blobs and updating the view
let compileButton = document.getElementById("compile-button");
let pdfViewer = document.getElementById("viewer");
// SET THIS WHEN UPDATING PDFJS
const pdfjsVersion = "2.9.359";
const viewerAdress = windowLocation + "/pdfjs-" + pdfjsVersion + "-dist/web/viewer.html?file=";

let compileFun = function() {
  let input = aceEditor.getValue();
  let encodedInput = encodeURIComponent(input);
  document.cookie = encodedInput;

  let selectedMacro = macrosSelector.value;

  let queryString = '/groffline?macro=' + selectedMacro + '&input=' + encodedInput;
  queryString = windowLocation + queryString;

  console.log("Sending query: " + queryString);
  
  let blob = 
  fetch(queryString)
  .then(response => response.blob())
  .then(blob => {
    let newBlob = new Blob([blob], {type: "application/pdf"})
    let pdfUrl = URL.createObjectURL(newBlob);
    pdfViewer.src = viewerAdress + pdfUrl;
  });
};

compileButton.onclick = compileFun;
compileFun();
