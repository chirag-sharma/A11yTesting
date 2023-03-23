let data = [];

function RenderHTMLReport() {
    let reportToRender = document.getElementById('report-container');
    let html = json2html.render(data, template);
    reportToRender.innerHTML = html;

    document.getElementById('upload').hidden = true;
    document.getElementById('footer').hidden = true;
}

function readFile(event) {
    document.getElementById('file-upload').disabled = true;

    const jsonFile = event.target.files[0];
    loadAsText(jsonFile);
}

function loadAsText(theFile) {
    const reader = new FileReader();
    reader.onload = function (loadedEvent) {
        data = loadedEvent.target.result;
    }
    reader.readAsText(theFile);
    document.getElementById('generate-report').disabled = false;
}
