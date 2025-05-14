function generarInforme() {
    const num_info = parseInt(document.getElementById("num_inf").value);
    const resultado = document.getElementById("resultado");
    if (isNaN(num_info)) {
        resultado.innerText = "Pon un número";
    } else if (num_info <= 0) {
        resultado.innerText = "El número debe ser mayor o igual a 1";
    } else {
        // Puedes añadir aquí la lógica de generación del Informe
        resultado.innerText = "Informe de la batalla " + num_info + " generado con exito";

        const xmlUrl = `batallas/batalla${num_info}.xml`;
        const xslUrl = `batallas/pruebas.xsl`;
        
        Promise.all([
        fetch(xmlUrl).then(res => {
          if (!res.ok) throw new Error("No se encontró el XML de la batalla.");
          return res.text();
        }),
        
        fetch(xslUrl).then(res => res.text())
      ])
      .then(([xmlStr, xslStr]) => {
        const parser = new DOMParser();
        const xml = parser.parseFromString(xmlStr, "text/xml");
        const xsl = parser.parseFromString(xslStr, "text/xml");

        const xsltProcessor = new XSLTProcessor();
        xsltProcessor.importStylesheet(xsl);
        const resultDocument = xsltProcessor.transformToFragment(xml, document);

        const contenedor = document.getElementById("Informe");
        contenedor.innerHTML = ""; // Limpia resultados anteriores
        contenedor.appendChild(resultDocument);
      })
      .catch(err => {
        document.getElementById("resultado").innerHTML = `<p style="color:red;">${err.message}</p>`;
      });
    }
}