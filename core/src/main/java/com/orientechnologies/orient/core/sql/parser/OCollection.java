/* Generated By:JJTree: Do not edit this line. OCollection.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=O,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package com.orientechnologies.orient.core.sql.parser;

import com.orientechnologies.orient.core.command.OCommandContext;
import com.orientechnologies.orient.core.db.record.OIdentifiable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OCollection extends SimpleNode {
  protected List<OExpression> expressions = new ArrayList<OExpression>();

  public OCollection(int id) {
    super(id);
  }

  public OCollection(OrientSql p, int id) {
    super(p, id);
  }

  /** Accept the visitor. **/
  public Object jjtAccept(OrientSqlVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }

  public void toString(Map<Object, Object> params, StringBuilder builder) {
    builder.append("[");
    boolean first = true;
    for (OExpression expr : expressions) {
      if (!first) {
        builder.append(", ");
      }
      expr.toString(params, builder);
      first = false;
    }
    builder.append("]");
  }

  public Object execute(OIdentifiable iCurrentRecord, OCommandContext ctx) {
    List<Object> result = new ArrayList<Object>();
    for (OExpression exp : expressions) {
      result.add(exp.execute(iCurrentRecord, ctx));
    }
    return result;
  }
}
/* JavaCC - OriginalChecksum=c93b20138b2ae58c5f76e458c34b5946 (do not edit this line) */
