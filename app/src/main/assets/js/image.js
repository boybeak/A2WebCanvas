function drawImage() {
    let image = ImageCreator.createImage();
    image.src = "assets/hippo.png";

    image.addEventListener("load", (e) => {
      ctx.drawImage(image, 0, 0);
    });
}
drawImage();