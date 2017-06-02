package fj.typeclasses.data;

import fj.F;
import fj.P2;
import fj.data.Either;
import fj.typeclasses.control.Category;
import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;

import static fj.Function.identity;
import static fj.P.p;
import static fj.data.Either.either_;

public interface Profunctor<p> {

  <A, B, C, D> __2<p, A, D> dimap(F<A, B> a2b, F<C, D> c2d, __<__<p, B>, C> b2c);

  default <A, B, C> __2<p, A, C> lmap(F<A, B> a2b, __<__<p, B>, C> b2c) {
    return dimap(a2b, identity(), b2c);
  }

  default <A, B, C> __2<p, A, C> rmap(F<B, C> b2c, __<__<p, A>, B> a2b) {
    return dimap(identity(), b2c, a2b);
  }

  default <A, B> __2<p, A, B> arr(Category<p> C, F<A, B> f) {
    return rmap(f, C.id());
  }

  interface Strong<p> extends Profunctor<p> {

    <A, B, C> __2<p, P2<A, C>, P2<B, C>> first(__<__<p, A>, B> pab);

    <A, B, C> __2<p, P2<A, B>, P2<A, C>> second(__<__<p, B>, C> pbc);

    default <A, B, C, D> __2<p, P2<A, C>, P2<B, D>> splitStrong(Category<p> C
      , __<__<p, A>, B> l
      , __<__<p, C>, D> r) {
      return C.composeFlipped(first(l), second(r));
    }

    default <A, B, C> __2<p, A, P2<B, C>> fanout(Category<p> C
    , __<__<p, A>, B> l
    , __<__<p, A>, C> r) {
      final __2<p, A, P2<A, A>> _split =
        dimap(identity(), a -> p(a, a), C.id());

      return C.composeFlipped(_split, splitStrong(C, l, r));
    }
  }

  interface Choice<p> extends Profunctor<p> {

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
}
