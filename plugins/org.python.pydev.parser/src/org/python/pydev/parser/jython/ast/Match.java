// Autogenerated AST node
package org.python.pydev.parser.jython.ast;

import org.python.pydev.parser.jython.SimpleNode;
import java.util.Arrays;

public final class Match extends stmtType {
    public exprType value;
    public stmtType[] body;

    public Match(exprType value, stmtType[] body) {
        this.value = value;
        this.body = body;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        result = prime * result + Arrays.hashCode(body);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Match other = (Match) obj;
        if (value == null) { if (other.value != null) return false;}
        else if (!value.equals(other.value)) return false;
        if (!Arrays.equals(body, other.body)) return false;
        return true;
    }
    @Override
    public Match createCopy() {
        return createCopy(true);
    }
    @Override
    public Match createCopy(boolean copyComments) {
        stmtType[] new0;
        if(this.body != null){
        new0 = new stmtType[this.body.length];
        for(int i=0;i<this.body.length;i++){
            new0[i] = (stmtType) (this.body[i] != null? this.body[i].createCopy(copyComments):null);
        }
        }else{
            new0 = this.body;
        }
        Match temp = new Match(value!=null?(exprType)value.createCopy(copyComments):null, new0);
        temp.beginLine = this.beginLine;
        temp.beginColumn = this.beginColumn;
        if(this.specialsBefore != null && copyComments){
            for(Object o:this.specialsBefore){
                if(o instanceof commentType){
                    commentType commentType = (commentType) o;
                    temp.getSpecialsBefore().add(commentType.createCopy(copyComments));
                }
            }
        }
        if(this.specialsAfter != null && copyComments){
            for(Object o:this.specialsAfter){
                if(o instanceof commentType){
                    commentType commentType = (commentType) o;
                    temp.getSpecialsAfter().add(commentType.createCopy(copyComments));
                }
            }
        }
        return temp;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("Match[");
        sb.append("value=");
        sb.append(dumpThis(this.value));
        sb.append(", ");
        sb.append("body=");
        sb.append(dumpThis(this.body));
        sb.append("]");
        return sb.toString();
    }

    @Override
    public Object accept(VisitorIF visitor) throws Exception {
        return visitor.visitMatch(this);
    }

    @Override
    public void traverse(VisitorIF visitor) throws Exception {
        if (value != null) {
            value.accept(visitor);
        }
        if (body != null) {
            for (int i = 0; i < body.length; i++) {
                if (body[i] != null) {
                    body[i].accept(visitor);
                }
            }
        }
    }

}
