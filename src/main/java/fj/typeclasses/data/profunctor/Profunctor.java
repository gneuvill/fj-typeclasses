package fj.typeclasses.data.profunctor;

import fj.F;
import fj.typeclasses.control.Category;
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


}
