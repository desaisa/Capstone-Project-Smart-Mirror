#path = 'C:/Users/sarth/Documents/login/days.txt'
#days_file = open(path,'r')
#output = days_file.read()
#print(output.split("\n"))
#days_file.close();

#path2 = 'C:/Users/sarth/Documents/login/new_days.txt'
#new_days_file = open(path,'w')
#new_days_file.write("Hello World")
#new_days_file.close();

import fileinput

def login_req(username, password):
    accounts_file = open("Accounts.txt",'r')
    tag = False
    for line in accounts_file:
        split_line = line.split(",")
        if(username == split_line[0]):
            tag = True
            if(password == split_line[1]):
                #print statement can be replaced by returning a value
                #print(username + " has logged in")
                return("0")
            else:
                #print statement can be replaced by returning a value
                #print("Wrong Password")
                return ("1")
    accounts_file.close()
    if (tag == False):
        #print statement can be replaced by returning a value
        #print("User does not exist")
        return ("2")
        

def create_new_acc(username, password, email):
    print("this is email: " + email)
    accounts_file = open("Accounts.txt",'r')
    for line in accounts_file:
        split_line = line.split(",")
        print(split_line[0])
        print(split_line[1])
        print(split_line[2])
        if(username == split_line[0]):
            #print statement can be replaced by returning a value
            print("Username is Taken")
            return "1"
        if(email == split_line[2]):
            print("Email is Taken")
            return "2"
        
    accounts_file.close();

    accounts_file = open("Accounts.txt",'a')
    #user,pass,email,weather,weather loc, news,sound,light
    accounts_file.write(username+","+password+","+email+",0"+",null"+",0"+",50"+",5\n")
    accounts_file.close()
    return "0"
    
    
def change_something(username,index,status):
    data = ""
    accounts_file = open("Accounts.txt",'r')
    for line in accounts_file:
        split_line = line.split(",")
        if(username == split_line[0]):
            data = data + split_line[0]
            for x in range(1,index):
                data = data + "," + split_line[x]
            data = data + "," + status
            for x in range(index+1,8):
                data = data + "," + split_line[x]
            continue
        data = data + line
    accounts_file.close();

    accounts_file = open("Accounts.txt",'w')
    #user,pass,email,weather,weather loc, news,sound,light
    accounts_file.write(data)
    accounts_file.close()
    return


'''def main():
    change_something("jaan3",4,"toronto")
        #login_req("sarth","4321")
        #create_new_acc("juju","4321","email9")

        


if __name__ == "__main__":
    main()'''



