// 管理者用の民宿登録ページ
const imageInput = document.getElementById('imageFile');
const imagePreview = document.getElementById('imagePreview');

imageImput.addEventListener('change', () => {
	if (imageInput.files[0]) {
		let fileReader = new FileReader();
		fileReader.onload = () => {
			imagePreview.innerHTML = '<img src="${fileReader.result}" class="mb-3">';
		}
		fileReader.readAsDataURL(imageInput.files[0]);
	} else {
		imagePreview.innerHTMLv= ';'
	}
})