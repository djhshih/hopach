library(hopach)

X <- read.table("../data/data-matrix.txt", header=FALSE, sep="\t")
D <- as.matrix( read.table("../data/distance-matrix.txt", header=FALSE, sep="\t") )
k <- 4

h <- hopach(X, dmat = D, clusters = "best", K = 15, kmax=4, khigh=4, col = "seq", mss = "med", initord = "co", ord = "neighbor")
write.table(h$clustering$labels, "hopach.txt", row.names=FALSE, col.names=FALSE, sep="\t")
saveRDS(h, "hopach.rds")

p <- pam(D, k)
write.table(p$clustering, "pam.txt", row.names=FALSE, col.names=FALSE, sep="\t")
saveRDS(p, "pam.rds")



X.sub <- read.table("../data/data-matrix_c56.txt", header=FALSE, sep="\t")
D.sub <- as.matrix( read.table("../data/distance-matrix_c56.txt", header=FALSE, sep="\t") )
k <- 2

h <- hopach(X.sub, dmat = D.sub, clusters = "best", K = 15, kmax=4, khigh=4, col = "seq", mss = "med", initord = "co", ord = "neighbor")
write.table(h$clustering$labels, "hopach_c56.txt", row.names=FALSE, col.names=FALSE, sep="\t")
saveRDS(h, "hopach_c56.rds")

p <- pam(D.sub, k)
write.table(p$clustering, "pam_c56.txt", row.names=FALSE, col.names=FALSE, sep="\t")
saveRDS(p, "pam_c56.rds")

