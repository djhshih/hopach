x <- read.table("hopach.txt", header=FALSE)[,1];
Y <- read.table("jhopach.txt", sep="\t", header=FALSE);

print( table(floor(x/10000), Y[,1]) );
print( table(floor(x/1000), Y[,2]) );
print( table(floor(x/100), Y[,3]) );


p.k2 <- read.table("pam_c56_k2.txt", header=FALSE)[,1];
p.k3 <- read.table("pam_c56_k3.txt", header=FALSE)[,1];

jp.k2 <- read.table("jpam_c56_k2.txt", header=FALSE)[,1];
jp.k3 <- read.table("jpam_c56_k3.txt", header=FALSE)[,1];

print( table(p.k2, jp.k2) );
print( table(p.k3, jp.k3) );


labelstomss(p.k2, as.hdist(D.sub), khigh=4, within="med")
labelstomss(jp.k2, as.hdist(D.sub), khigh=4, within="med")

labelstomss(p.k3, as.hdist(D.sub), khigh=4, within="med")
labelstomss(jp.k3, as.hdist(D.sub), khigh=4, within="med")

