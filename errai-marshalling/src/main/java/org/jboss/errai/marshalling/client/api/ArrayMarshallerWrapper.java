package org.jboss.errai.marshalling.client.api;

import org.jboss.errai.marshalling.client.api.json.EJValue;
import org.jboss.errai.marshalling.client.marshallers.AbstractNullableMarshaller;
import org.jboss.errai.marshalling.client.marshallers.ListMarshaller;

import java.util.Arrays;

/**
 * A marshaller that wraps another marshaller, producing and consuming arrays of
 * objects handled by that marshaller.
 *
 * @author Christian Sadilek <csadilek@redhat.com>
 * @author Jonathan Fuerth <jfuerth@gmail.com>
 */
public class ArrayMarshallerWrapper extends AbstractNullableMarshaller<Object> {

  private final Marshaller<?> wrappedMarshaller;
  
  public ArrayMarshallerWrapper(final Marshaller<?> wrappedMarshaller) {
    this.wrappedMarshaller = wrappedMarshaller;
  }

//  /**
//   * Throws UnsupportedOperationException.
//   */
//  @Override
//  public Class<Object> getTypeHandled() {
//    throw new UnsupportedOperationException("Not implemented");
//  }

  @Override
  public Object doNotNullDemarshall(final EJValue o, final MarshallingSession ctx) {
    return ListMarshaller.INSTANCE.demarshall(o, ctx).toArray(wrappedMarshaller.getEmptyArray());
  }

  @Override
  public String doNotNullMarshall(final Object o, final MarshallingSession ctx) {
    return ListMarshaller.INSTANCE.marshall(Arrays.asList((Object[]) o), o.getClass().getName(), ctx);
  }

  @Override
  public Object[] getEmptyArray() {
    throw new UnsupportedOperationException("Not implemented, but should create an array with n+1 dimensions");
  }
}