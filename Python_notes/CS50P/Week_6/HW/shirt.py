import sys
from os.path import splitext
from PIL import Image, ImageOps

imageType = (".jpeg", ".jpg", ".png")

if len(sys.argv) < 3:
    sys.exit("Too few command-line arguments")
elif len(sys.argv) > 3:
    sys.exit("Too many command-line arguments")
# elif not (splitext(sys.argv[1])[-1] in imageType and splitext(sys.argv[2])[-1] in imageType):
elif not (sys.argv[1].endswith(imageType) and sys.argv[2].endswith(imageType)):
    sys.exit("Invalid output")
elif splitext(sys.argv[1])[-1] != splitext(sys.argv[2])[-1]:
    sys.exit("Input and output have different extensions")


try:
    shirt = Image.open(sys.argv[1])
    picture = Image.open(sys.argv[2])
    shirtSize = shirt.size

    # according to the size of shirt to crop the picture
    croppedImage = ImageOps.fit(picture, shirtSize)

    # 没进行过裁剪的照片再paste
    picture.paste(shirt,shirt)
    picture.show()
    # 才建国后的照片再paste
    croppedImage.paste(shirt,shirt)
    croppedImage.show()
except FileNotFoundError:
    sys.exit("Input does not exist")