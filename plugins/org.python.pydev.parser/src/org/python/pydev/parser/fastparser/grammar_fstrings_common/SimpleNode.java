/* Generated By:JJTree: Do not edit this line. SimpleNode.java Version 4.3 */
/* JavaCCOptions:MULTI=false,NODE_USES_PARSER=true,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=*,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.python.pydev.parser.fastparser.grammar_fstrings_common;

import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.python.pydev.core.log.Log;
import org.python.pydev.parser.grammar_fstrings.FStringsGrammar;
import org.python.pydev.parser.grammar_fstrings.FStringsGrammarTreeConstants;
import org.python.pydev.shared_core.string.StringUtils;
import org.python.pydev.shared_core.string.TextSelectionUtils;
import org.python.pydev.shared_core.structure.LowMemoryArrayList;

public class SimpleNode implements Node, Iterable<SimpleNode> {

    protected Node parent;
    protected Node[] children;
    public final int id;
    protected Object value;

    public int beginColumn;
    public int endColumn;
    public int beginLine;
    public int endLine;

    public SimpleNode(int i) {
        id = i;
    }

    public static Node jjtCreate(FStringsGrammar p, int id) {
        return new SimpleNode(id);
    }

    @Override
    public void jjtOpen(FStringsGrammar parser) {
    }

    @Override
    public void jjtClose(FStringsGrammar parser) {
    }

    @Override
    public void jjtSetParent(Node n) {
        parent = n;
    }

    @Override
    public Node jjtGetParent() {
        return parent;
    }

    @Override
    public void jjtAddChild(Node n, int i) {
        if (children == null) {
            children = new Node[i + 1];
        } else if (i >= children.length) {
            Node c[] = new Node[i + 1];
            System.arraycopy(children, 0, c, 0, children.length);
            children = c;
        }
        children[i] = n;
    }

    @Override
    public Node jjtGetChild(int i) {
        return children[i];
    }

    @Override
    public int jjtGetNumChildren() {
        return (children == null) ? 0 : children.length;
    }

    public void jjtSetValue(Object value) {
        this.value = value;
    }

    public Object jjtGetValue() {
        return value;
    }

    /* You can override these two methods in subclasses of SimpleNode to
     customize the way the node appears when the tree is dumped.  If
     your output uses more than one line you should override
     toString(String), otherwise overriding toString() is probably all
     you need to do. */

    @Override
    public String toString() {
        return StringUtils.join("", FStringsGrammarTreeConstants.jjtNodeName[id], " (", beginLine, ", ",
                beginColumn, ") -> (", endLine, ", ", endColumn, ")");
    }

    public String toString(String prefix) {
        return prefix + toString();
    }

    /* Override this method if you want to customize how the node dumps
     out its children. */

    public void dump(String prefix) {
        this.dump(prefix, null);
    }

    public void dump(String prefix, IDocument doc) {
        String contentsFromString = "";
        if (doc != null) {
            if (this.beginColumn > 0 && this.beginLine > 0) {
                try {
                    contentsFromString = " - " + getContentsFromString(doc);
                } catch (BadLocationException | RuntimeException e) {
                    Log.log(e);
                }
            }
        }
        System.out.println(toString(prefix) + contentsFromString);
        if (children != null) {
            for (int i = 0; i < children.length; ++i) {
                SimpleNode n = (SimpleNode) children[i];
                if (n != null) {
                    n.dump(prefix + " ", doc);
                }
            }
        }
    }

    public String getContentsFromString(IDocument doc) throws BadLocationException {
        int offset1 = TextSelectionUtils.getAbsoluteCursorOffset(doc, this.beginLine - 1, this.beginColumn - 1);
        int offset2 = TextSelectionUtils.getAbsoluteCursorOffset(doc, this.endLine - 1, this.endColumn);
        return doc.get(offset1, offset2 - offset1);
    }

    @Override
    public Iterator<SimpleNode> iterator() {
        return new Iterator<SimpleNode>() {
            int i = 0;

            @Override
            public boolean hasNext() {
                return i < jjtGetNumChildren();
            }

            @Override
            public SimpleNode next() {
                SimpleNode child = (SimpleNode) jjtGetChild(i);
                i++;
                return child;
            }
        };
    }

    public List<SimpleNode> collectChildren(int id) {
        LowMemoryArrayList<SimpleNode> ret = new LowMemoryArrayList<SimpleNode>();
        this.collectChildren(id, ret);
        return ret;
    }

    /**
     * Visits the whole tree collecting nodes of a given id.
     */
    public void collectChildren(int id, List<SimpleNode> ret) {
        if (this != null) {
            int numChildren = this.jjtGetNumChildren();
            for (int i = 0; i < numChildren; i++) {
                SimpleNode child = (SimpleNode) this.jjtGetChild(i);
                if (child.id == id) {
                    ret.add(child);
                }
                child.collectChildren(id, ret);
            }
        }
    }
}

/* JavaCC - OriginalChecksum=169f55ab032ee70675f83cac2ef6f376 (do not edit this line) */
