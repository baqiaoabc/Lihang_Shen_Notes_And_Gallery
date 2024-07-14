from PIL import Image

# images = [Image.open("costume1.gif"), Image.open("costume2.gif")]
images = [Image.open("1.jpg"), Image.open("2.jpg"), Image.open("3.jpg")]

images[0].save(
    "number_result.gif", save_all=True, append_images=[images[2], images[1]], duration=500, loop=0
)
images[0].show()