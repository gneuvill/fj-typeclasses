package fj.typeclasses.data.profunctor;

import fj.data.Either;
import fj.typeclasses.control.Category;
import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;

import static fj.Function.identity;
import static fj.data.Either.either_;

public interface Choice<p> extends Profunctor<p> {

  <A, B, C> __2<p, Either<A, C>, Either<B, C>> left(__<__<p, A>, B> pab);

  <A, B, C> __2<p, Either<A, B>, Either<A, C>> right(__<__<p, B>, C> pab);

  default <A, B, C, D> __2<p, Either<A, C>, Either<B, D>> splitChoice(Category<p> C
    , __<__<p, A>, B> l
    , __<__<p, C>, D> r) {
    return C.composeFlipped(left(l), right(r));
  }

  default <A, B, C> __2<p, Either<A, B>, C> fanin(Category<p> C
    , __<__<p, A>, C> l
    , __<__<p, B>, C> r) {
    final __2<p, Either<C, C>, C> _join =
      dimap(either_(identity(), identity()), identity(), C.id());

    return C.composeFlipped(splitChoice(C, l, r), _join);
  }
}
