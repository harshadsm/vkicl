<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>

<html>
<head>
</head>
<body>

	<script type="text/javascript"
		src="http://code.jquery.com/jquery-1.11.3.min.js"></script>

	<form enctype="multipart/form-data" name="uploadForm">
		<input name="file" type="file" /> <input type="button" value="Upload"
			name="upload" />
	</form>
	<progress value="0"></progress>

	<script type="text/javascript">
		$("[name='upload']").click(
				function() {
					var formData = new FormData($("[name='uploadForm']")[0]);
					$.ajax({
						url : './upload',
						type : 'POST',
						xhr : function() {
							var myXhr = $.ajaxSettings.xhr();
							if (myXhr.upload) {
								myXhr.upload.addEventListener('progress',
										progressHandlingFunction, false);
							}
							return myXhr;
						},
						success : function() {
							alert("success")
						},
						error : function() {
							alert("error")
						},
						data : formData,
						cache : false,
						contentType : false,
						processData : false
					});
				});

		function progressHandlingFunction(e) {
			if (e.lengthComputable) {
				$('progress').attr({
					value : e.loaded,
					max : e.total
				});
			}
		}
	</script>

</body>
</html>