function drawImage() {
    let image = ImageCreator.createImage();
    image.src = "assets/hippo.png";
    ctx.drawImage(image, 0, 0);
}
drawImage();