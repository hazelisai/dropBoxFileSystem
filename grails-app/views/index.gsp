<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="main"/>
	<title>DropBoxTest</title>

</head>
<body>
<h1>Guardar imagen</h1>
<g:uploadForm controller="dropbox" action="guardarImagen" name="dropbox_upload">
	<label>Archivo</label>
	<p></p>
	<input type="file" name="file" id="file"><p></p>
	<button type="submit">Guardar</button>
</g:uploadForm>
</body>
</html>
