package fj.data.profunctor;

import fj.F;
import fj.P;
import fj.P2;
import fj._Fj;
import fj.control.Category;
import fj.data.Functor;
import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;

import static fj.Function.identity;
import static fj.P.p;

public interface Strong<p> extends Profunctor<p> {

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

  static FStrong f() { return () -> {}; }

  interface FStrong extends Strong<F.µ>, FProfunctor {
    @Override
    default <A, B, C> F<P2<A, C>, P2<B, C>> first(__<__<F.µ, A>, B> pab) {
      return ac -> P.p(_Fj.asF(pab).f(ac._1()), ac._2());
    }

    @Override
    default <A, B, C> F<P2<A, B>, P2<A, C>> second(__<__<F.µ, B>, C> pbc) {
      return ab -> Functor.<A>p2().map(_Fj.asF(pbc), ab);
    }
  }
}
