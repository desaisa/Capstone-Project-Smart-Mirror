#!/bin/bash
# volume control

# min=-10239, max=400; but can't hear anything below -1800
# 100%, val=400
# 99%, val=294
# 95%, val=-131
# 90%, val=-663
# 80%, val=-1727

case $1 in

    'on')
        amixer cset numid=1 75% # 75%, val=-2259
        ;;

    'off')
        amixer cset numid=1 0% # 0%, val=-10239
        ;;

    *)
        amixer cset numid=1 $1% # any percentage value
        ;;
esac