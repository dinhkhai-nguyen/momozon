import {
    Route,
    Routes
} from 'react-router-dom'

import Header from './components/Header/Header.jsx'
import CategoryPage from './pages/CategoryPage/CategoryPage.jsx'
import HomePage from './pages/HomePage/HomePage.jsx'
import SearchPage from './pages/SearchPage/SearchPage.jsx'

function App() {
    return (
        <>
            <Header />

            <Routes>
                <Route
                    path="/"
                    element={<HomePage />}
                />

                <Route
                    path="/category/:categoryId"
                    element={<CategoryPage />}
                />

                <Route
                    path="/search"
                    element={<SearchPage />}
                />
            </Routes>
        </>
    )
}

export default App