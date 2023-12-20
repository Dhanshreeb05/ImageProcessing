# load the image "demo-image.jpg" and call it demoImage
load res/demo-image.jpg demoImage

# save the image to the path specified
save res/image-files/demoImage.jpg demoImage

# flip demoImage horizontally
horizontal-flip demoImage demo-horizontal
save res/image-files/demo-horizontal.jpg demo-horizontal

# flip demoImage vertically
vertical-flip demoImage demo-vertical
save res/image-files/demo-vertical.jpg demo-vertical

# flip demo-horizontal Vertically
vertical-flip demo-horizontal demo-horizontal-vertical
save res/image-files/demo-horizontal-vertical.jpg demo-horizontal-vertical

# brighten demoImage by 15
brighten 15 demoImage demo-bright
save res/image-files/demo-bright.jpg demo-bright

# brighten demoImage by 150
brighten 150 demoImage demo-too-bright
save res/image-files/demo-too-bright.jpg demo-too-bright

# darken demoImage by 15
brighten -15 demoImage demo-dark
save res/image-files/demo-dark.jpg demo-dark

# darken demoImage by 150
brighten -150 demoImage demo-too-dark
save res/image-files/demo-too-dark.jpg demo-too-dark

#create a greyscale using only the value component, as an image demo-greyscale
value-component demoImage demo-greyscale-value
save res/image-files/demo-greyscale-value.jpg demo-greyscale-value

#create a greyscale using only the Intensity component, as an image demo-greyscale
intensity-component demoImage demo-greyscale-intensity
save res/image-files/demo-greyscale-intensity.jpg demo-greyscale-intensity

#create a greyscale using only the Luma component, as an image demo-greyscale
luma-component demoImage demo-greyscale-luma
save res/image-files/demo-greyscale-luma.jpg demo-greyscale-luma

# split the image into 3 channels
rgb-split demoImage demo-split-red demo-split-green demo-split-blue
save res/image-files/demo-split-red.jpg demo-split-red
save res/image-files/demo-split-green.jpg demo-split-green
save res/image-files/demo-split-blue.jpg demo-split-blue

# get only the red component of the image
red-component demoImage demo-red split 30
save res/image-files/demo-red.jpg demo-red

# get only the green component of the image
green-component demoImage demo-green split 30
save res/image-files/demo-green.jpg demo-green

# get only the blue component of the image
blue-component demoImage demo-blue split 30
save res/image-files/demo-blue.jpg demo-blue

# combine 3 channels into one image
rgb-combine demo-combine demo-red demo-green demo-blue
save res/image-files/demo-combine.jpg demo-combine

# blur the image
blur demoImage demo-blur split 30
save res/image-files/demo-blur.jpg demo-blur

# blur the image twice
blur demo-blur demo-blur-again split 30
save res/image-files/demo-blur-again.jpg demo-blur-again

# sharpen the image
sharpen demoImage demo-sharpen split 30
save res/image-files/demo-sharpen.jpg demo-sharpen

# sharpen the image twice
sharpen demo-sharpen demo-sharpen-again split 30
save res/image-files/demo-sharpen-again.jpg demo-sharpen-again

# convert the image to sepia
sepia demoImage demo-sepia split 30
save res/image-files/demo-sepia.jpg demo-sepia

# rotate image clockwise
rotate-clockwise demoImage demo-rotate-clockwise
save res/image-files/demo-clockwise.jpg demo-rotate-clockwise

# rotate image clockwise
rotate-anticlockwise demoImage demo-rotate-anticlockwise
save res/image-files/demo-anticlockwise.jpg demo-rotate-anticlockwise

# Do compress
compress 20 demoImage demo-compress-20
save res/image-files/demo-compress-20.jpg demo-compress-20

histogram demo-compress-20 demo-compress-20-histogram
save res/image-files/demo-compress-20-histogram.jpg demo-compress-20-histogram

compress 100 demoImage demo-compress-100
save res/image-files/demo-compress-100.jpg demo-compress-100

histogram demo-compress-100 demo-compress-100-histogram
save res/image-files/demo-compress-100-histogram.jpg demo-compress-100-histogram

# get Histogram
histogram demoImage demo-histogram
save res/image-files/demo-histogram.jpg demo-histogram

# Color correct the image
color-correct demoImage demo-color-correct
save res/image-files/demo-color-correct.jpg demo-color-correct

histogram demo-color-correct demo-color-correct-histogram
save res/image-files/demo-color-correct-histogram.jpg demo-color-correct-histogram

# level adjust the image
levels-adjust 5 123 255 demoImage demo-levels-adjust-5-123-255
save res/image-files/demo-levels-adjust-5-123-255.jpg demo-levels-adjust-5-123-255

histogram demo-levels-adjust-5-123-255 demo-levels-adjust-5-123-255-histogram
save res/image-files/demo-levels-adjust-5-123-255-histogram.jpg demo-levels-adjust-5-123-255-histogram


levels-adjust 10 123 250 demoImage demo-levels-adjust-10-123-250
save res/image-files/demo-levels-adjust-10-123-250.jpg demo-levels-adjust-10-123-250

histogram demo-levels-adjust-10-123-250 demo-levels-adjust-10-123-250-histogram
save res/image-files/demo-levels-adjust-10-123-250-histogram.jpg demo-levels-adjust-10-123-250-histogram


# exit the Application
exit

