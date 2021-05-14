// Autogenerated AST node
package org.python.pydev.parser.jython.ast;

import org.python.pydev.parser.jython.SimpleNode;
import java.util.Arrays;

public final class MatchOr extends exprType {
    public exprType[] patterns;

    public MatchOr(exprType[] patterns) {
        this.patterns = patterns;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(patterns);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        MatchOr other = (MatchOr) obj;
        if (!Arrays.equals(patterns, other.patterns)) return false;
        return true;
    }
    @Override
    public MatchOr createCopy() {
        return createCopy(true);
    }
    @Override
    public MatchOr createCopy(boolean copyComments) {
        exprType[] new0;
        if(this.patterns != null){
        new0 = new exprType[this.patterns.length];
        for(int i=0;i<this.patterns.length;i++){
            new0[i] = (exprType) (this.patterns[i] != null?
            this.patterns[i].createCopy(copyComments):null);
        }
        }else{
            new0 = this.patterns;
        }
        MatchOr temp = new MatchOr(new0);
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
        StringBuffer sb = new StringBuffer("MatchOr[");
        sb.append("patterns=");
        sb.append(dumpThis(this.patterns));
        sb.append("]");
        return sb.toString();
    }

    @Override
    public Object accept(VisitorIF visitor) throws Exception {
        return visitor.visitMatchOr(this);
    }

    @Override
    public void traverse(VisitorIF visitor) throws Exception {
        if (patterns != null) {
            for (int i = 0; i < patterns.length; i++) {
                if (patterns[i] != null) {
                    patterns[i].accept(visitor);
                }
            }
        }
    }

}
