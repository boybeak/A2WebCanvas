function drawCreatePattern() {
    let img = ImageCreator.createImage();
    img.src = "assets/hippo.png";
    // Only use the image after it's loaded
    img.onload = () => {
      const pattern = ctx.createPattern(img, "repeat-x");
      ctx.fillStyle = pattern;
      ctx.fillRect(0, 0, 900, 900);
    };
}
drawCreatePattern();