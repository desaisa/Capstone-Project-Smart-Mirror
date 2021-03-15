#!/bin/bash
# Light control

# GPIO pin 17 = red
# GPIO pin 22 = green
# GPIO pin 24 = blue

case $1 in

    '0'|'off')
        pigs w 17 0 w 22 0 w 24 0 # turn off 0%	
        ;;

    '10')
        pigs p 17 255 p 22 255 p 24 255 # turn on 100%	
        ;;

    '9')
        pigs p 17 230 p 22 230 p 24 230 # 90%
        ;;

    '8')
        pigs p 17 204 p 22 204 p 24 204 # 80%
        ;;

    '7'|'on')
        pigs p 17 179 p 22 179 p 24 179 # 70%
        ;;

    '6')
        pigs p 17 153 p 22 153 p 24 153 # 60%
        ;;

    '5')
        pigs p 17 128 p 22 128 p 24 128 # 50%
        ;;

    '4')
        pigs p 17 102 p 22 102 p 24 102 # 40%
        ;;

    '3')
        pigs p 17 77 p 22 77 p 24 77 # 30%
        ;;

    '2')
        pigs p 17 51 p 22 51 p 24 51 # 20%
        ;;

    '1')
        pigs p 17 26 p 22 26 p 24 26 # 10%
        ;;

    'r')
        pigs p 17 128 p 22 0 p 24 0 # red 50%
        ;;

    'g')
        pigs p 17 0 p 22 128 p 24 0 # green 50%
        ;;

    'b')
        pigs p 17 0 p 22 0 p 24 128 # blue 50%
        ;;

    *)
        pigs p 17 0 p 22 0 p 24 0 # off
        ;;
esac