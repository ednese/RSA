
long n_puissance_x_mod_c(long n , long x,long c){
	long a= 1;
	for(int i=1;i<=x;i++){
		a= a * n;
		a=a%c;
	}
	return a;
}//Pour calculer la reste de (n puissance x) divise par c .On va l'utiliser dans l'étape "Cryptage" et "décryptage" . 

long[]semi_cryp(String message){ 
	int l = message.length();
	long []cryp = new long[l];
	for (int i=0;i<l;i++){
		cryp[i]=message.charAt(i);
	}
	return cryp;
}//Pour prendre le code ASCII de chaque lettre dans le message que l'on a entré , et le mémoriser dans un tableau .L'étape préparatoire de cryptage . .
long premier(long a) {
   double r = sqrt(a) +2;
   long n = (long)r;
   long v =1;
   if ((a %6 ==1) || (a %6 ==5)) {
      for (int i =5; i < n; i +=6) {
         if ((a % i ==0) || (a % (i +2) ==0)) {
            v = 0;
         }
      }
      return  v;
   } else {
      return  0;
   }
}//Pour déterminer si un nombre est premier ou non .On va l'utiliser pour initialiser les 2 nombre premier "p" et "q" .
long [] init_pq() {
   long p = random(2, 1000);
   long q = random(2, 1000);
   long []pre = new long[2];
   while (premier(p) ==0) {
      p = random(2, 1000);
   }
   while (premier(q) ==0) {
      q = random(2, 1000);
   }
   pre[0] = q;
   pre[1] = p;
   return (pre);
}//Initialisation des 2 nombres premiers
long []key_Pub(long[]pq) {
   long phi_n = (pq[0] -1) * (pq[1] -1);
   int np=0;
   long np1=phi_n;
   long s =random(2,1000000);
   while(s>phi_n){s =random(2,1000000);
   }
   long p=1;
   long o =0;
      while ((phi_n % s ==0)||(0==1)) {
         s=random(2 , 1000000);
          while(s>phi_n){ s =random(2,1000000);
   }
         for (int i =2 ;i< phi_n ; i++){
         	if((phi_n%i==0) && (s%i==0)){
         		 p=1;
         	}
         	o=p;
         	p=0;
         
         }
      }
   long[]key = new long[2];
   key[0] = pq[0]*pq[1];
   key[1] = s;
   return  key;
}//Pour calculer la clé publique (n,e) .
long []key_Pri(long[]pq, long e) {
   long keys[] = new long[2];
   long phi_n = (pq[0] -1) * (pq[1] -1);
   long d = 0;
   long i =1;
   long j=e*i;
   while((j % phi_n !=1)&&(i<99999))
       {
        i++;
        j=e*i;
      }
      d=i;
   keys[0] =  pq[0]*pq[1];
   keys[1] = d;
   javax.swing.JOptionPane.showMessageDialog(null,"clé publique n="+keys[1]);
   return  keys;
}//Pour calculer la clé privéé (n,d)
long []cryptage(String message,long n,long e) {
   int l = message.length();
	long []cr1 = new long[l];
	long []cr2 = new long[l];
	cr1 =semi_cryp(message);
	print("Code après la cryptage :");
	for(int i = 0 ; i<l ; i++){
		cr2[i]=n_puissance_x_mod_c(cr1[i],e,n);
		print(cr2[i]+" ");
	}
	println();
   return cr2;
}//Cryptage du message ( c= (m puissance e) % n )
String decryptage(long cr[],long d,long n,int l){
	long []cr1 = new long[l];
	char []cr2 = new char[l];
	String s="";
	for (int i=0;i<l;i++){
		cr1[i]=n_puissance_x_mod_c(cr[i],d,n);
		cr2[i]=(char)cr1[i];
		s=s+cr2[i];
		}
	return s;
}//Décryptage du message ( m= (c puissance e) % n )

void main(){
	long [] pq= new long[2];
	long []keypri=new long[2];
	long [] keypub= new long[2];
	keypri[1]=99999;
	while(keypri[1]==99999){
	pq=init_pq();
	keypub=key_Pub(pq);
	keypri=key_Pri(pq,keypub[1]);
	}
	long n = keypri[0];
	long e = keypub[1];
	long d = keypri[1];
	boolean u=true;
	boolean g=true;
	while (u==true){
	String m = readString("Entrer le message à crypter :");
	int l =m.length();
	long []cr=new long[l];
	cr = cryptage(m,n,e);
	g=readBoolean("Decrypter ?");
	if (g==true){
	println("Decrypter :"+decryptage(cr,d,n,l));}
	u=readBoolean("Vous voulez entrer un autre message?");
	}
}
