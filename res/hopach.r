library(hopach)

X <- read.table("../data/data-matrix.txt", header=FALSE, sep="\t");
D <- as.matrix( read.table("../data/distance-matrix.txt", header=FALSE, sep="\t") );
k <- 8;

h <- hopach(X, dmat = D, clusters = "best", K = 15, col = "seq", mss = "med", initord = "co", ord = "neighbor")
write.table(h$clustering$labels, "hopach.txt", row.names=FALSE, col.names=FALSE, sep="\t")
saveRDS(h, "hopach.rds")

p <- pam(D, k)
write.table(p$clustering, "pam.txt", row.names=FALSE, col.names=FALSE, sep="\t")
saveRDS(p, "pam.rds")
