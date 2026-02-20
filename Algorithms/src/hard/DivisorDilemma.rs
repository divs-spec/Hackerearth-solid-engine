use std::io::{self, Read};

struct SegTree {
    n: usize,
    t: Vec<i64>,
}

impl SegTree {
    fn new(n: usize) -> Self {
        SegTree {
            n,
            t: vec![0; 2 * n],
        }
    }

    fn update(&mut self, mut p: usize, v: i64) {
        p += self.n;
        self.t[p] = v;
        while p > 1 {
            p >>= 1;
            self.t[p] = self.t[p << 1] + self.t[p << 1 | 1];
        }
    }

    fn query(&self, mut l: usize, mut r: usize) -> i64 {
        let mut res = 0;
        l += self.n;
        r += self.n;
        while l < r {
            if (l & 1) == 1 {
                res += self.t[l];
                l += 1;
            }
            if (r & 1) == 1 {
                r -= 1;
                res += self.t[r];
            }
            l >>= 1;
            r >>= 1;
        }
        res
    }
}

fn main() {
    // ---------- Fast Input ----------
    let mut input = String::new();
    io::stdin().read_to_string(&mut input).unwrap();
    let mut it = input.split_whitespace();

    const MAXN: usize = 2_000_001;

    // ---------- Sum of Divisors ----------
    let mut sum_of_div = vec![0i64; MAXN];
    for i in 1..MAXN {
        let mut j = i;
        while j < MAXN {
            sum_of_div[j] += i as i64;
            j += i;
        }
    }

    // ---------- Order by sum of divisors ----------
    let mut pairs: Vec<(i64, usize)> = (1..MAXN)
        .map(|i| (sum_of_div[i], i))
        .collect();

    pairs.sort_unstable();

    let mut order = vec![0usize; MAXN];
    for (idx, &(_, v)) in pairs.iter().enumerate() {
        order[v] = idx;
    }

    // ---------- Read Queries ----------
    let q: usize = it.next().unwrap().parse().unwrap();
    let mut queries = Vec::with_capacity(q);
    for i in 0..q {
        let n: usize = it.next().unwrap().parse().unwrap();
        let m: i64 = it.next().unwrap().parse().unwrap();
        queries.push((n, m, i));
    }

    // Sort descending by N
    queries.sort_unstable_by(|a, b| b.0.cmp(&a.0));

    let mut ans = vec![0i64; q];

    // ---------- Segment Trees ----------
    let mut seg_count = SegTree::new(MAXN);
    let mut seg_sum = SegTree::new(MAXN);

    let mut qi: isize = queries.len() as isize - 1;

    // ---------- Process ----------
    for i in 1..MAXN {
        let pos = order[i];
        seg_count.update(pos, 1);
        seg_sum.update(pos, sum_of_div[i]);

        while qi >= 0 && queries[qi as usize].0 == i {
            let (_, m, idx) = queries[qi as usize];
            qi -= 1;

            let mut low = 0usize;
            let mut high = MAXN - 1;
            let mut best = 0usize;

            while low <= high {
                let mid = (low + high) >> 1;
                if seg_count.query(mid, MAXN) >= m {
                    best = mid;
                    low = mid + 1;
                } else {
                    if mid == 0 { break; }
                    high = mid - 1;
                }
            }

            ans[idx] = seg_sum.query(best, MAXN);
        }
    }

    // ---------- Output ----------
    let mut out = String::new();
    for v in ans {
        out.push_str(&format!("{}\n", v));
    }
    print!("{}", out);
}
