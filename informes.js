function generarInforme() {
    const num_info = parseInt(document.getElementById("num_inf").value);
    const resultado = document.getElementById("resultado");
    const informe = document.getElementById("Informe");

    if (isNaN(num_info)) {
        resultado.innerText = "Pon un número";
    } else if (num_info <= 0) {
        resultado.innerText = "El número debe ser mayor o igual a 1";
    } else {
        // Puedes añadir aquí la lógica de generación del informe
        resultado.innerText = "Informe generado con exito " + num_info;
        informe.innerText = "Informe generado para la batalla número " + num_info;

        
    }
}