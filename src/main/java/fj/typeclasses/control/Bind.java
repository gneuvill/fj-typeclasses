package fj.typeclasses.control;

import fj.F;
import org.derive4j.hkt.__;

import static fj.Function.identity;

public interface Bind<m> extends Apply<m> {

    <A, B> __<m, B> bind(__<m, A> ma, F<A, __<m, B>> f);

    default <A, B> __<m, B> bindFlipped(F<A, __<m, B>> f, __<m, A> ma) {
        return bind(ma, f);
    }

    default <A> __<m, A> join(__<m, __<m, A>> ma) {
        return bind(ma, identity());
    }

    default <A, B, C> F<A, __<m, C>> composeK(F<A, __<m, B>> f, F<B, __<m, C>> g) {
        return a -> bind(f.f(a), g);
    }

    default <A, B, C> F<A, __<m, C>> oK(F<A, __<m, B>> f, F<B, __<m, C>> g) {
        return composeK(f, g);
    }

    default <A, B, C> F<A, __<m, C>> composeKFlipped(F<B, __<m, C>> f, F<A, __<m, B>> g) {
        return composeK(g, f);
    }

    default <A, B, C> F<A, __<m, C>> oKFlipped(F<B, __<m, C>> f, F<A, __<m, B>> g) {
        return composeKFlipped(f, g);
    }

    default <A> __<m, A> ifM(__<m, Boolean> cond, __<m, A> t, __<m, A> f) {
        return bind(cond, _cond -> _cond ? t : f);
    }
}
