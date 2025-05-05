console.log('Archivo js_index.js cargado');

window.addEventListener('load', function() { 
    console.log('Ventana completamente cargada');

    document.querySelectorAll('.portada h1, .portada p').forEach(function(element) { 
        element.style.opacity = 1; 
    }); 
});