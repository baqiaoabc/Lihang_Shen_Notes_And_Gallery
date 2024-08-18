import random
import copy

def GenerateParity(information):
    parity=0
    for i in range(len(information)):
        if information[i]==1:
            parity=1-parity
            ####Here, our aim to get the parity bit. At the beginning the parity bit should
            ####be zero. If there is one more "1", the parity bit should be flipped. 
    return parity


def ErrorChannel(coded):
    received=copy.copy(coded) #or use received=coded[:], do not use received=code
    for i in range(len(coded)):
        rand=random.random()
        if rand<=0.1:### With probability 0.1, the bit is flipped. 
            received[i]=1-received[i]
            ####Here, our aim to randomly flip the bit. The probability that rand<0.1 is 0.1
            #####(rand is uniformly distributed in [0,1]). With probability 0.1, the bit is flipped.  
    return received

def CheckParity(received):
    parity=0
    for i in range(len(received)):
        if received[i]==1:
            parity=1-parity;

    if parity==0:
        return True
    else:
        return False
   
        
        
        

result=[0,0,0]

for i in range(10000):

    information=[]
    infolength=5
    for i in range(infolength):
        rand=random.randint(0,1)
        information.append(rand)
    parity=GenerateParity(information)

    coded=copy.copy(information)
    coded.append(parity)
    received=ErrorChannel(coded)
    check=CheckParity(received)




    if (coded==received) & check:
        #If coded == received, no error happens. The count of event A is increased by 1
        result[0]=result[0]+1
    elif (coded!=received) & check:
        #If coded != received, and check==True (i.e., the parity check thinks that there is no error. ),
        #error happens but it is not detected by the parity check. The count of event C is increased by 1.
        result[1]=result[1]+1
    elif (coded!=received) & (not check):
        #If coded != received, and check==False (i.e., the parity check thinks that there is an error. ),
        #error happens and it is detected by the parity check. The count of event B is increased by 1.
        result[2]=result[2]+1



print ("Event A probability", float(result[0])/float(sum(result)))
print ("Event B probability", float(result[2])/float(sum(result)))
print ("Event C probability", float(result[1])/float(sum(result)))




