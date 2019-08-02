export function getRand(m,n){
    return Math.ceil(Math.random()*10000)%(n-m+1)+m;
}