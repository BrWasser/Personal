import { Route, Routes } from 'react-router-dom'
import PaginaInicial from './pages/pagina_inicial'
import Algoritmo from './pages/algoritmo'
import TelaCadastro from './pages/cadastro'
import Contato from './pages/contato'
import Entrar from './pages/entrar'

const Router = () => {
    return (
        <Routes>
            <Route path="/" element={<PaginaInicial />} />
            <Route path="/algoritmo" element={<Algoritmo />} />
            <Route path="/contato" element={<Contato />} />
            <Route path="/cadastrar" element={<TelaCadastro />} />
            <Route path="/entrar" element={<Entrar />} />
        </Routes>
    )
}

export default Router