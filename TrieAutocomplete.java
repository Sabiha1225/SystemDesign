
package com.mycompany.trie_stream_ofcharacters;

public class TrieAutocomplete {

    class Trie {
        Trie[] child;
        boolean isEnd;
        int count;
        
        public Trie() {
            child = new Trie[26];
            isEnd = false;
            count = 0;
        }
    }
    
    Trie root;
    
    private void insert(String word)
    {
        Trie pNode = root;
        for(char c : word.toCharArray())
        {
            if(pNode.child[c - 'a'] == null)
            {
                pNode.child[c-'a'] = new Trie();
            }
            pNode = pNode.child[c-'a'];
        }
        pNode.isEnd = true;
        pNode.count++;

    }
    
    private void createRootNode()
    {
        root = new Trie();
    }
    
    private boolean isLastNode(Trie node)
    {
        for(int i=0; i<26; ++i)
        {
            if(node.child[i] != null)
                return false;
        }
        
        return true;
    }
    
    private void foundAllSuggestions(Trie pNode, String query) {
        if(pNode.isEnd)
        {
            System.out.println(query);
        }
        if(isLastNode(pNode)) return;
        
        StringBuilder sb = new StringBuilder(query);
        for(int i=0; i<26; ++i)
        {
            if(pNode.child[i] != null)
            {
                char c =  (char)('a' + i);
                sb.append(c);
                foundAllSuggestions(pNode.child[i], sb.toString());
                sb.deleteCharAt(sb.length()-1);
            }
        }
        
    }
    
    private int printSuggestions(String query)
    {
        Trie pNode = root;
        for(char c : query.toCharArray())
        {
            if(pNode.child[c-'a'] == null)
                return 0;
            pNode = pNode.child[c-'a'];
        }
        
        boolean isWord = pNode.isEnd;
        boolean isLast = isLastNode(pNode);
        if(isWord && isLast)
        {
            System.out.println(query);
            return -1;
        }
        
        if(!isLast)
        {
            foundAllSuggestions(pNode, query);
            return 1;
        }
        return 0;
    }
    
    public static void main(String[] args)
    {
        TrieAutocomplete TA = new TrieAutocomplete();
        TA.createRootNode();
        TA.insert("hello");
        TA.insert("dog");
        TA.insert("hell");
        TA.insert("cat");
        TA.insert("a");
        TA.insert("hel");
        TA.insert("help");
        TA.insert("helps");
        TA.insert("helping");
        
        int res = TA.printSuggestions("hel");
        if(res == -1 )
            System.out.println("No other strings found with this prefix");
        else if(res == 0)
            System.out.println("No string found with this prefix");
    }
}
