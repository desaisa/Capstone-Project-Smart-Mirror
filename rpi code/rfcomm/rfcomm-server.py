from bluetooth import *
from authentication import *
from youtube import *
from notube import *
import os
import threading

weather_pid =0
str_list = ""

def set_username(name):
    global username
    username = name

def btrw():
    while True:
        print("This is the main pid " + str(os.getpid()))
        server_sock=BluetoothSocket(RFCOMM)
        server_sock.bind(("",PORT_ANY))
        server_sock.listen(1)

        port = server_sock.getsockname()[1]

        print ("listening on port %d" % port)

        uuid = "965401d5-b903-44cf-8dfb-4406a4290630"

        advertise_service(server_sock,"Sample Server", service_id = uuid, service_classes =[uuid, SERIAL_PORT_CLASS],profiles=[SERIAL_PORT_PROFILE],)

        print ("Waiting connection on RFCOMM channel ",port)

        client_sock,address = server_sock.accept()
        print ("Accepted connection from ",address)



        try:
            while True:
                print("Receiving")
                data = client_sock.recv(1024)#bytes
                if not data:
                    break
                print("Received",data)
                data_to_send = interpreter(data)#string
                print(data_to_send)
                print("Sending")
                client_sock.send(data_to_send);
        except OSError:
            pass

        client_sock.close()
        server_sock.close()
    
    
def interpreter(data):
    received_str = data.decode("utf-8")#convert bytes to string
    str_list = received_str.split(":")#split the fields
    
    #The first field is the tag
    if str_list[0]=="LOGIN":
        set_username(str_list[1])
        login_result = login_req(str_list[1],str_list[2])
        if login_result == "0":
            openup()
        return login_result
    elif str_list[0]=="CREATE":
        return create_new_acc(str_list[1],str_list[2],str_list[3]+"\n")
    elif str_list[0] == "YOUTUBE":
        try:
            kill_youtube()
        except:
            print("youtube process cannot be terminated")
        return play_youtube(str_list[1])
    elif str_list[0] == "NOTUBE":
        return kill_youtube()
    elif str_list[0] == "WEATHER":
        change_something(username,3,"1")
        change_something(username,4,str_list[1])
        weather_thread = threading.Thread(target = weather_report,name=weather_report)
        weather_thread.daemon = True
        weather_thread.start()
        #weather_pid = weather_thread.get_ident()
        return "0"
    elif str_list[0] == "NOWEATHER":
        change_something(username,3,"0")
        os.system("sudo pkill -f weather")
        return "1"
    elif str_list[0] == "NEWS":
        change_something(username,5,"1")
        news_thread = threading.Thread(target = news_report,name=news_report)
        news_thread.daemon = True
        news_thread.start()
        return "1"
    elif str_list[0] == "NONEWS":
        change_something(username,5,"0")
        os.system("sudo pkill -f news")
        return "1"
    elif str_list[0] == "SOUND":
        change_something(username,7,str_list[1])
        os.system("do sound shit" + str_list[1])
        return "1"
    elif str_list[0] == "LIGHT":
        change_something(username,6,str_list[1])
        os.system("do light shit" + str_list[1])
        return "1"
    elif str_list[0] == "ONWEATHER":
        weather_thread = threading.Thread(target = weather_report,name=weather_report)
        weather_thread.daemon = True
        weather_thread.start()
        return "1"
    elif str_list[0] == "SETWEATHER":
        accounts_file = open("Accounts.txt",'r')
        for line in accounts_file:
            split_line = line.split(",")
            if(username == split_line[0]):
                weather = split_line[3]
                break
        accounts_file.close()
        return weather
    else:  
        return "This is bullshit"
    
def weather_report():
    accounts_file = open("Accounts.txt",'r')
    for line in accounts_file:
        split_line = line.split(",")
        if(username == split_line[0]):
            location = split_line[4]
            break
        
    accounts_file.close()
    if(location == "null"):
        return
    os.system("sudo pkill -f weather")
    os.system("python3 /home/pi/SmartMirror/weather/weather.py " + location)
    
def news_report():
    os.system("sudo pkill -f news")
    os.system("python3 /home/pi/SmartMirror/news.py")
    
def openup:
    


def main():
        btrw()

        

if __name__ == "__main__":
    main()
