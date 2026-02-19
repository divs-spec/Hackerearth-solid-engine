import Foundation

let MOD = 1_000_000_007

// ---------- Fast Input ----------
final class FastScanner {
    private var data: [Substring] = []
    private var idx: Int = 0

    func next() -> String {
        while idx >= data.count {
            data = readLine()!.split(separator: " ")
            idx = 0
        }
        defer { idx += 1 }
        return String(data[idx])
    }

    func nextInt() -> Int {
        return Int(next())!
    }
}

// ---------- Trie Node ----------
final class TrieNode {
    var data: Character
    var children: [Character: TrieNode] = [:]

    init(_ data: Character) {
        self.data = data
    }
}

// ---------- Trie ----------
final class Trie {
    private let root = TrieNode("2") // dummy root

    func addWord(_ s: String) {
        var curr = root
        for ch in s {
            if curr.children[ch] == nil {
                curr.children[ch] = TrieNode(ch)
            }
            curr = curr.children[ch]!
        }
    }

    func dfs(_ node: TrieNode, _ zeroesSeen: Int, _ seenZero: Bool) -> Int {
        let isZero = (node.data == "0")
        let newSeenZero = seenZero || isZero
        let newZeroesSeen = (zeroesSeen + (isZero ? 1 : 0)) % MOD

        var ans = 0
        if newSeenZero {
            ans = (newZeroesSeen - 1 + MOD) % MOD
        }

        for child in node.children.values {
            ans = (ans + dfs(child, newZeroesSeen, newSeenZero)) % MOD
        }

        return ans
    }

    func solve() -> Int {
        return dfs(root, 0, false)
    }
}

// ---------- Main ----------
let scanner = FastScanner()
let n = scanner.nextInt()

let trie = Trie()
for _ in 0..<n {
    trie.addWord(scanner.next())
}

print(trie.solve())
