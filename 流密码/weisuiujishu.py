def lfsr_keySteam(initState,n):
    key=[0]*n
    for i in range(len(initState)):
        key[i]=initState[i]
    for i in range(len(key)-len(initState)):
        key[5+i]=key[3+i]^key[i]
    print(f"密钥流：{key}")
    return key

def encrypt(plaintext,key):
    group=[]
    date=[]
    while len(key)//7<=len(plaintext):
        key+=key
    for i in range(0,len(key),7):
        group=key[i:i+7]
        num=0
        for j in group :
            num=(num<<1) | j
        date.append(num)
    c=""
    for i in range(len(plaintext)):
        c+=chr(ord(plaintext[i])^date[i])
    return c

plaintext="abc" 
initState=[1,1,0,0,1]
Key=lfsr_keySteam(initState,31)
print(f"明文：{plaintext}",end=" ")
print(f"密文：{encrypt(plaintext,Key)}")
plaintext1="abcabcabc"
print(f"明文：{plaintext1}",end=" ")
print(f"密文：{encrypt(plaintext1,Key)}")