package fj.data.profunctor;

import fj.F;
import fj.F1W;
import fj._Fj;
import fj.control.Category;
import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;

import static fj.Function.identity;

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

  static FProfunctor f() { return () -> {}; }

  interface FProfunctor extends Profunctor<F.µ> {
    void self();

    @Override
    default <A, B, C, D> F<A, D> dimap(F<A, B> a2b, F<C, D> c2d, __<__<F.µ, B>, C> b2c) {
      return F1W.lift(c2d).o(_Fj.asF(b2c)).o(a2b);
    }
  }
}
