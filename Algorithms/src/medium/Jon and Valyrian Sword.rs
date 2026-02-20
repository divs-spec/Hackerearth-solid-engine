/*
// Sample code to perform I/O:

use std::io;
use std::io::prelude::*;

fn main() {
    let mut name = String::new();
    io::stdin().read_line(&mut name).unwrap();          // Reading input from STDIN
    println!("Hi, {}.", name);                          // Writing output to STDOUT
}

// Warning: Printing unwanted or ill-formatted data to output will cause the test cases to fail
*/

// Write your code here
use std::io::{self, Read};

fn main() {
    // Fast input
    let mut input = String::new();
    io::stdin().read_to_string(&mut input).unwrap();
    let mut it = input.split_whitespace();

    let n: usize = it.next().unwrap().parse().unwrap();
    let x: i64 = it.next().unwrap().parse().unwrap();
    let total_special = 2 * x;

    // Read special nodes
    let mut is_special = vec![0i64; n + 1];
    for _ in 0..total_special {
        let v: usize = it.next().unwrap().parse().unwrap();
        is_special[v] = 1;
    }

    // Read edges
    let mut adj = vec![Vec::<(usize, usize)>::new(); n + 1];
    for idx in 1..n {
        let u: usize = it.next().unwrap().parse().unwrap();
        let v: usize = it.next().unwrap().parse().unwrap();
        adj[u].push((v, idx));
        adj[v].push((u, idx));
    }

    let mut visited = vec![false; n + 1];
    let mut best_value: i64 = -1;
    let mut best_index: usize = 0;

    fn dfs(
        u: usize,
        adj: &Vec<Vec<(usize, usize)>>,
        visited: &mut Vec<bool>,
        is_special: &Vec<i64>,
        total_special: i64,
        best_value: &mut i64,
        best_index: &mut usize,
    ) -> i64 {
        visited[u] = true;
        let mut cnt = is_special[u];

        for &(v, idx) in &adj[u] {
            if !visited[v] {
                let sub = dfs(
                    v,
                    adj,
                    visited,
                    is_special,
                    total_special,
                    best_value,
                    best_index,
                );

                let crossings = sub * (total_special - sub);

                if crossings > *best_value
                    || (crossings == *best_value && idx > *best_index)
                {
                    *best_value = crossings;
                    *best_index = idx;
                }

                cnt += sub;
            }
        }
        cnt
    }

    dfs(
        1,
        &adj,
        &mut visited,
        &is_special,
        total_special,
        &mut best_value,
        &mut best_index,
    );

    println!("{}", best_index);
}
