group.range <- c(5, 6 + 1);
obs.per.group <- 30;

index.range <- obs.per.group * (group.range-1) + 1;
idx <- index.range[1]:(index.range[2]-1);

X <- read.table("../data/data-matrix.txt", sep="\t", header=FALSE)
D <- read.table("../data/distance-matrix.txt", sep="\t", header=FALSE);

X.sub <- X[idx, ];
D.sub <- D[idx, idx];

write.table(X.sub, "../data/data-matrix_c56.txt", row.names=FALSE, col.names=FALSE, sep="\t");
write.table(as.matrix(D.sub), "../data/distance-matrix_c56.txt", row.names=FALSE, col.names=FALSE, sep="\t");

