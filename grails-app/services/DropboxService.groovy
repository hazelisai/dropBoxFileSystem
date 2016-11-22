import com.dropbox.core.DbxException
import com.dropbox.core.DbxRequestConfig
import com.dropbox.core.v2.DbxClientV2
import com.dropbox.core.v2.files.FileMetadata
import com.dropbox.core.v2.files.ListFolderResult
import com.dropbox.core.v2.files.Metadata
import com.dropbox.core.v2.users.FullAccount
import grails.transaction.Transactional
import org.springframework.web.multipart.MultipartFile

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Transactional
class DropboxService {
    def grailsApplication


    //obtiene el cliente dropbox
    private DbxClientV2 obtenerCliente(String token){
        // Create Dropbox client
        DbxRequestConfig config = new DbxRequestConfig("dropbox/java-tutorial", "en_US")
        DbxClientV2 client = new DbxClientV2(config, token)
        return client
    }

    //convierte un archivo multipartFile a inputStream
    private InputStream convertirMultipartFile(MultipartFile file){
        byte [] byteArr=file.getBytes()
        InputStream inputStream = new ByteArrayInputStream(byteArr)
        return inputStream
    }

    public String guardarArchivo(MultipartFile file,String dropbox_path) throws DbxException, IOException {
        //convertimos el archivo
        InputStream inputStream = convertirMultipartFile(file)

        //obtenemos el cliente de dropbox
        String ACCESS_TOKEN = "${grailsApplication.config.karimnot.dropbox.access_token}"
        DbxClientV2 client = obtenerCliente(ACCESS_TOKEN)

        //generamos la ruta y el nombre
        String timeStamp = createTimestampString()
        String nombre = "${timeStamp}_${file.getOriginalFilename()}"
        String ruta_completa = "${dropbox_path}/${nombre}"

        // Subimos la imagen a Dropbox
        FileMetadata metadata = client.files().uploadBuilder(ruta_completa).uploadAndFinish(inputStream);
        println metadata

        return ruta_completa
    }

    public Boolean eliminarArchivo(String dropbox_file_path){
        Boolean bandera = false
        //obtenemos el cliente de dropbox
        String ACCESS_TOKEN = "${grailsApplication.config.karimnot.dropbox.access_token}"
        DbxClientV2 client = obtenerCliente(ACCESS_TOKEN)

        FileMetadata metadata = client.files().delete(dropbox_file_path)
        println(metadata)
        bandera = true
        return bandera
    }

    private String createTimestampString() {
        LocalDateTime timestamp = LocalDateTime.now()
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy_HH-mm-ss");
        return timestamp.format(formatter);
    }


}
