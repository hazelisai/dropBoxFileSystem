

import org.springframework.web.multipart.MultipartFile

class DropboxController {
    def dropboxService

    def index() { }

    def guardarImagen(){
        MultipartFile file = request.getFile('file')

        if(file.isEmpty()) {
           render "Es necesario un archivo"
            return 0
        }

        File carpeta = new File("web-app/archivosDropBox/")
        carpeta.mkdirs()

        String nombre = file.getOriginalFilename()
        file.transferTo(new File("web-app/archivosDropBox/${nombre}"))
        println("se transfirio la imagen al file sistem")

        dropboxService.guardarArchivo(file,grailsApplication.config.karimnot.dropbox.profesores_path)
        render "archivo guardado :)"
    }

    String obtenerExtension(String nombre) {
        String extension=""
        int i= nombre.lastIndexOf('.')
        if (i>0) {
            extension = nombre.substring(i + 1)
        }
        return extension
    }
}
