package fj.data.profunctor;

import fj.data.Either;
import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;

/**
 *  The {@link Cochoice} class provides the dual operations of the {@link Choice} class.
 */
public interface Cochoice<p> extends Profunctor<p> {

  <A, B, C> __2<p, A, B> unleft(__<__<p, Either<A, C>>, Either<B, C>> p);

  <A, B, C> __2<p, B, C> unright(__<__<p, Either<A, B>>, Either<A, C>> p);
}
